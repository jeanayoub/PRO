package extfx.scene.control;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.CheckBoxTreeItem;
import javafx.scene.control.TreeCell;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.control.cell.CheckBoxTreeCell;
import javafx.util.Callback;
import extfx.util.CheckBoxHierarchyData;

/**
 * <p>{@link TreeViewWithItems} rendering each item as a {@link CheckBoxTreeItem}
 * making use of {@link CheckBoxHierarchyData#isEnabled()} and
 * {@link CheckBoxHierarchyData#setEnabled(boolean)}.</p>
 * <p>Note: If you do not supply a custom cell factory via
 * {@link TreeView#setCellFactory(Callback)} the default
 * {@link CheckBoxTreeCell} will be used.</p>
 *
 * @author eckig
 * @param <T>
 *            type of items contained in TreeView
 * @since 31.10.2013
 */
public class CheckBoxTreeViewWithItems<T extends CheckBoxHierarchyData<T>> extends TreeViewWithItems<T> {

	/**
     * Constructor, creating an empty root node and a default cell factory.
     * @since 31.10.2013
     */
    public CheckBoxTreeViewWithItems() {
        this(new CheckBoxTreeItem<T>());
    }
	
	/**
	 * Constructor, creating a default cell factory
	 * @param pRootNode root node
	 */
	public CheckBoxTreeViewWithItems(CheckBoxTreeItem<T> pRootNode) {
		this(pRootNode, new Callback<TreeView<T>, TreeCell<T>>(){
			@Override
			public TreeCell<T> call(TreeView<T> arg0) {
				return new CheckBoxTreeCell<>();
			}
		});
	}
	
	/**
	 * Constructor
	 * @param pRootNode root node
	 * @param pCellFactory cell factory
	 */
	public CheckBoxTreeViewWithItems(CheckBoxTreeItem<T> pRootNode, Callback<TreeView<T>, TreeCell<T>> pCellFactory) {
		super(pRootNode);
		setCellFactory(pCellFactory);
	}
	
    @Override
    protected TreeItem<T> createTreeItem(final T pValue) {
        CheckBoxTreeItem<T> item = new CheckBoxTreeItem<T>();
        item.setValue(pValue);
        item.setExpanded(true);
        if (pValue != null) {
            item.selectedProperty().set(pValue.isEnabled());
            item.selectedProperty().addListener(new ChangeListener<Boolean>() {
                @Override
                public void changed(ObservableValue<? extends Boolean> pArg0, Boolean pArg1, Boolean pArg2) {
                    if (pArg2 != null && !pArg2.equals(pArg1)) {
                        pValue.setEnabled(pArg2.booleanValue());
                    }
                }
            });
        }
        return item;
    }
}
