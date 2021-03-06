package org.sharp.swing.utils;

import java.awt.Component;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.util.EventObject;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.event.CellEditorListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
import javax.swing.text.JTextComponent;

import org.apache.commons.beanutils.PropertyUtils;
import org.sharp.jdkex.Utils;

public class BeanUiUtils {

	static interface TableCellData {
		Object object();

		String propertyName();
	}
	
	public static JPanel beanUi(final Object obj){
		final JPanel panel = SwingUtils.newPanel(null);
		Utils.introspect(obj, new Utils.PropertyHandler() {
			public void property(Object obj, PropertyDescriptor p) {
			}
			public <T>void simpleProperty(final Object obj,final String pName, T pValue, Class<T> pType) {
				panel.add(new JLabel(pName));
				final JTextComponent comp = SwingUtils.newUi(pType,pValue);
				comp.addFocusListener(new FocusListener() {
					public void focusLost(FocusEvent e) {
						try {
							PropertyUtils.setSimpleProperty(obj, pName, comp.getText());
						} catch (Exception e1) {
							e1.printStackTrace();
						}
					}
					public void focusGained(FocusEvent e) {
					}
				});
				panel.add(comp,"wrap,width 70%!");
			}
		});
		return panel;
	}

	public static JTable tableUi(final Object obj)
			throws IntrospectionException, IllegalArgumentException,
			IllegalAccessException, InvocationTargetException {
		DefaultTableModel model = new DefaultTableModel();
		model.addColumn("name");
		model.addColumn("value");

		BeanInfo info = (BeanInfo) Introspector.getBeanInfo(obj.getClass());
		PropertyDescriptor[] pds = info.getPropertyDescriptors();

		for (int i = 0; i < pds.length; i++) {
			PropertyDescriptor p = pds[i];
			final String pName = p.getName();
			Class pt = p.getPropertyType();
			Utils.log.info("property:"
					+ (pt == null ? "indexed_property" : pt.getSimpleName())
					+ " " + pName);
			if (!"class".equalsIgnoreCase(pName) && p.getReadMethod() != null)
				try {
					model.addRow(new Object[] { p, new TableCellData() {
						public Object object() {
							return obj;
						}

						public String propertyName() {
							return pName;
						}

					} });
				} catch (Exception e) {
					Utils.log.error("", e);
				}
		}

		return new JTable(model) {
			private static final long serialVersionUID = 1L;

			public javax.swing.table.TableCellEditor getCellEditor(final int row,
					final int column) {
				final Object value = getModel().getValueAt(row, column);
				if (value != null) {
					Utils.log.info("getCellEditor(" + row + "," + column
							+ ")");
					return new TableCellEditor() {
						Component editorComponent;
						int rown = row;
						int columnn = column;

						public Component getTableCellEditorComponent(
								JTable table, Object value, boolean isSelected,
								int row, int column) {
							if (value instanceof PropertyDescriptor) {
								editorComponent = new JLabel(
										((PropertyDescriptor) value).getName());
							} else if (value instanceof TableCellData) {
								TableCellData data = (TableCellData) value;
								String str = "";
								try {
									Object pValue = PropertyUtils.getProperty(
											data.object(), data.propertyName());
									if (pValue != null)
										str = pValue.toString();
								} catch (Exception e) {
									e.printStackTrace();
								}
								editorComponent = new JTextField(str);
							} else {
								editorComponent = new JTextField(
										value.toString());
							}
							return editorComponent;
						}

						public Object getCellEditorValue() {
							Utils.log.info("getCellEditorValue("+rown+","+columnn+"):" + value);
							return value;
						}

						public boolean isCellEditable(EventObject anEvent) {
							Utils.log.info("isCellEditable("+rown+","+columnn+")");
							return (value instanceof TableCellData);
						}

						public boolean shouldSelectCell(EventObject anEvent) {
							Utils.log.info("shouldSelectCell("+rown+","+columnn+")");
							return false;
						}

						public boolean stopCellEditing() {
							try {
								TableCellData data = (TableCellData) value;
								if (editorComponent instanceof JTextField) {
									String newValue = ((JTextField) editorComponent)
											.getText();
									Utils.log.info("new value("+rown+","+columnn+"):" + newValue);
									Class type = PropertyUtils.getPropertyType(
											data.object(), data.propertyName());
									if(type.isInstance(newValue))
										PropertyUtils.setProperty(data.object(),
											data.propertyName(), newValue);
									 
								}
							} catch (Exception e) {
								e.printStackTrace();
							}
							return true;
						}

						public void cancelCellEditing() {
							Utils.log.info("cancelCellEditing("+rown+","+columnn+")");
						}

						public void addCellEditorListener(CellEditorListener l) {
						}

						public void removeCellEditorListener(
								CellEditorListener l) {
						}
					};
				} else
					return super.getCellEditor(row, column);
			};

			public javax.swing.table.TableCellRenderer getCellRenderer(int row,
					int column) {
				Object value = getModel().getValueAt(row, column);
				if (value != null) {
					Utils.log.info("getCellRenderer(" + row + "," + column
							+ ")");
					return new TableCellRenderer() {
						public Component getTableCellRendererComponent(
								JTable table, Object value, boolean isSelected,
								boolean hasFocus, int row, int column) {
							if (value instanceof PropertyDescriptor) {
								return new JLabel(
										((PropertyDescriptor) value).getName());
							}
							if (value instanceof TableCellData) {
								TableCellData data = (TableCellData) value;
								String str = "";
								try {
									Object pValue = PropertyUtils.getProperty(
											data.object(), data.propertyName());
									if (pValue != null)
										str = pValue.toString();
								} catch (Exception e) {
									e.printStackTrace();
								}
								return new JLabel(str);
							} else {
								return new JLabel(value.toString());
							}
						}
					};
				} else
					return super.getCellRenderer(row, column);
			};
		};
	}

}
