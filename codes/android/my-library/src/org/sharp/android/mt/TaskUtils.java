package org.sharp.android.mt;

import java.io.File;
import java.util.concurrent.atomic.AtomicBoolean;

import org.sharp.android.view.ViewUtils;
import org.sharp.intf.DownloadEventListener;
import org.sharp.intf.PMessage;
import org.sharp.intf.UnzipEventListener;
import org.sharp.utils.IOUtils;
import org.sharp.utils.IOUtils.DownloadInfo;
import org.sharp.utils.IOUtils.DownloadInfoReceiver;
import org.sharp.utils.IOUtils.UnzipEventReceiver;
import org.sharp.utils.Option;
import org.sharp.utils.Wrapper;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.os.Handler;
import android.os.Message;
import android.widget.Toast;

public class TaskUtils {
	private static Handler newPMessageHandler(final Context ctx,
			final Class<DownloadInfo> msgObjType,
			final Wrapper<CancelableThread> wrapper,
			final DownloadEventListener del) {
		Handler handler = new Handler() {
			ProgressDialog progressDialog;
			Option<OnCancelListener> ocl = new Option<OnCancelListener>(
					new OnCancelListener() {
						@Override
						public void onCancel(DialogInterface dialog) {
							Toast.makeText(ctx, ctx.getString(my.library.R.string.toast_cancel_download), 
									1).show();
							wrapper.value().cancel();
						}
					});

			@Override
			public void handleMessage(Message msg) {
				DownloadInfo di = msgObjType.cast(msg.obj);
				switch (msg.what) {
				case PMessage.WHAT_START:
					if (di.fileSize == DownloadInfo.UNKNOWN_CONTENT_LENGTH) {
						progressDialog = ViewUtils.showProgressDialog(ctx,
								ctx.getString(my.library.R.string.dlg_title_downloading),
								ProgressDialog.STYLE_SPINNER, ocl);
					} else {
						progressDialog = ViewUtils.showProgressDialog(ctx,
								ctx.getString(my.library.R.string.dlg_title_downloading),
								ProgressDialog.STYLE_HORIZONTAL, ocl);
						progressDialog.setMax(di.fileSize);
					}
					break;
				case PMessage.WHAT_UPDATE_PROGRESS:
					if (di.fileSize == DownloadInfo.UNKNOWN_CONTENT_LENGTH) {
						;
					} else {
						progressDialog.setProgress(di.downloadedBytes);
						//int result = di.downloadedBytes * 100 / di.fileSize;
						//progressDialog.setMessage(result + "%");
					}
					break;
				case PMessage.WHAT_COMPLETE:
					progressDialog.dismiss();
//					Toast.makeText(ctx, 
//							ctx.getString(my.library.R.string.toast_download_finished),
//							1).show();
					del.complete(di.filePath);
					break;
				case PMessage.WHAT_CANCELED:
//					Toast.makeText(ctx, 
//							ctx.getString(my.library.R.string.toast_download_canceled),
//							1).show();
					progressDialog.dismiss();
					break;
				case PMessage.WHAT_ERROR:
					Toast.makeText(ctx, di.exception.getMessage(), 1).show();
					progressDialog.dismiss();
					break;
				}
				;
				super.handleMessage(msg);
			}
		};
		return handler;
	}

	private static Handler newPMessageHandler(final Context ctx,
			final String zipfilePath, final String todir,
			final Wrapper<CancelableThread> wrapper,
			final UnzipEventListener uel) {
		Handler handler = new Handler() {
			ProgressDialog progressDialog;
			Option<OnCancelListener> ocl = new Option<OnCancelListener>(
					new OnCancelListener() {
						@Override
						public void onCancel(DialogInterface dialog) {
							Toast.makeText(ctx, 
									ctx.getString(my.library.R.string.toast_cancel_unzip), 
									1).show();
							wrapper.value().cancel();
						}
					});

			@Override
			public void handleMessage(Message msg) {
				switch (msg.what) {
				case PMessage.WHAT_START:
					progressDialog = ViewUtils.showProgressDialog(ctx, 
							ctx.getString(my.library.R.string.dlg_title_unzipping), 
							ProgressDialog.STYLE_SPINNER, ocl);
					break;
				case PMessage.WHAT_COMPLETE:
//					Toast.makeText(ctx, 
//							ctx.getString(my.library.R.string.toast_unzip_finished), 
//							1).show();
					progressDialog.dismiss();
					uel.complete(zipfilePath);
					break;
				case PMessage.WHAT_CANCELED:
//					Toast.makeText(ctx, 
//							ctx.getString(my.library.R.string.toast_unzip_canceled), 
//							1).show();
					progressDialog.dismiss();
					break;
				case PMessage.WHAT_ERROR:
					Toast.makeText(ctx, 
							ctx.getString(my.library.R.string.toast_unzip_error), 
							1).show();
					progressDialog.dismiss();
					break;
				}
				super.handleMessage(msg);
			}
		};
		return handler;
	}

	public static void unzipFile(Context ctx, String filePath, String toDir,
			UnzipEventListener uel) {
		Wrapper<CancelableThread> threadWrapper = new Wrapper<CancelableThread>();
		Handler handler = newPMessageHandler(ctx, filePath, toDir,
				threadWrapper, uel);
		CancelableThread unzipThread = newUnzipThread(filePath, toDir, handler);
		threadWrapper.set(unzipThread);
		unzipThread.thread().start();
	}

	public static void downCancelableFile(Context ctx, String urL, String path,
			DownloadEventListener del) {
		Wrapper<CancelableThread> threadWrapper = new Wrapper<CancelableThread>();
		Handler handler = newPMessageHandler(ctx, DownloadInfo.class,
				threadWrapper, del);
		CancelableThread downloadThread = newDownloadThread(urL, path, handler);
		threadWrapper.set(downloadThread);
		downloadThread.thread().start();
	}

	public static interface CancelableThread {
		void cancel();

		Thread thread();
	}

	private static CancelableThread newUnzipThread(final String filePath,
			final String toDir, final Handler handler) {
		final AtomicBoolean cancelFlag = new AtomicBoolean(false);
		final Thread downloadThread = new Thread() {
			public void run() {
				File df = new File(filePath);
				IOUtils.unzip(df, new File(toDir), new UnzipEventReceiver() {
					@Override
					public void startUnzip() {
						Message msg = new Message();
						msg.what = PMessage.WHAT_START;
						handler.sendMessage(msg);
					}

					@Override
					public void completeUnzip() {
						Message msg = new Message();
						msg.what = PMessage.WHAT_COMPLETE;
						handler.sendMessage(msg);
					}

					@Override
					public void errorUnzip(Exception e) {
						Message msg = new Message();
						msg.what = PMessage.WHAT_ERROR;
						handler.sendMessage(msg);
					}

					@Override
					public void canceled() {
						Message msg = new Message();
						msg.what = PMessage.WHAT_CANCELED;
						handler.sendMessage(msg);
					}

				}, cancelFlag);
			}
		};

		return new CancelableThread() {
			@Override
			public void cancel() {
				cancelFlag.set(true);
			}

			@Override
			public Thread thread() {
				return downloadThread;
			}
		};
	}

	private static CancelableThread newDownloadThread(final String urL,
			final String path, final Handler handler) {
		final AtomicBoolean cancelFlag = new AtomicBoolean(false);
		final Thread downloadThread = new Thread() {
			public void run() {
				IOUtils.downFile(urL, path, new DownloadInfoReceiver() {
					@Override
					public void startReceive(DownloadInfo di) {
						Message msg = new Message();
						msg.what = PMessage.WHAT_START;
						msg.obj = di;
						handler.sendMessage(msg);
					}

					@Override
					public void newBytesSaved(DownloadInfo di) {
						Message msg = new Message();
						msg.what = PMessage.WHAT_UPDATE_PROGRESS;
						msg.obj = di;
						handler.sendMessage(msg);
					}

					@Override
					public void errorOccur(DownloadInfo di) {
						Message msg = new Message();
						msg.what = PMessage.WHAT_ERROR;
						msg.obj = di;
						handler.sendMessage(msg);
					}

					@Override
					public void complete(DownloadInfo di) {
						Message msg = new Message();
						msg.what = PMessage.WHAT_COMPLETE;
						msg.obj = di;
						handler.sendMessage(msg);
					}

					@Override
					public void canceled(DownloadInfo di) {
						Message msg = new Message();
						msg.what = PMessage.WHAT_CANCELED;
						msg.obj = di;
						handler.sendMessage(msg);
					}
				}, cancelFlag);
			}
		};

		return new CancelableThread() {
			@Override
			public void cancel() {
				cancelFlag.set(true);
			}

			@Override
			public Thread thread() {
				return downloadThread;
			}
		};
	}

}
