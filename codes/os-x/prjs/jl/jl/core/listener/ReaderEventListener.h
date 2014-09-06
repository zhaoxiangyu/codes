#ifndef READEREVENTLISTENER_H
#define READEREVENTLISTENER_H


class ReaderEventListener
{
	public:
		ReaderEventListener();
		virtual ~ReaderEventListener();

		void viewNeedsFresh();
		void showError(int errorCode);
	protected:
	private:
};

#endif // READEREVENTLISTENER_H
