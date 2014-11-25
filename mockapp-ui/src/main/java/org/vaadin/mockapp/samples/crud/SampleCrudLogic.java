package org.vaadin.mockapp.samples.crud;

import org.vaadin.mockapp.MockAppUI;
import org.vaadin.mockapp.samples.backend.DataService;
import org.vaadin.mockapp.samples.data.Product;

import com.vaadin.server.Page;

/**
 * This class provides an interface for the logical operations between the CRUD
 * view, its parts like the product editor form and the data source, including
 * fetching and saving products.
 * 
 * Having this separate from the view makes it easier to test various parts of
 * the system separately, and to e.g. provide alternative views for the same
 * data.
 */
public class SampleCrudLogic {

	private SampleCrudView view;

	public SampleCrudLogic(SampleCrudView simpleCrudView) {
		this.view = simpleCrudView;
	}

	public void init() {
		editProduct(null);
		// Hide and disable if not admin
		if (!MockAppUI.get().getAccessControl().isUserInRole("admin")) {
			view.setNewProductEnabled(false);
		}

		refreshTable();
	}

	public void cancelProduct() {
		setFragmentParameter("");
		view.selectRow(null);
	}

	/**
	 * Update the fragment without causing navigator to change view
	 */
	private void setFragmentParameter(String productId) {
		String fragmentParameter;
		if (productId == null || productId.isEmpty()) {
			fragmentParameter = "";
		} else {
			fragmentParameter = productId;
		}

		Page page = MockAppUI.get().getPage();
		page.setUriFragment("!" + SampleCrudView.VIEW_NAME + "/"
				+ fragmentParameter, false);
	}

	public void enter(String productId) {
		if (productId != null && !productId.isEmpty()) {
			if (productId.equals("new"))
				newProduct();
			else {
				// Ensure this is selected even if coming directly here from
				// login
				try {
					int pid = Integer.parseInt(productId);
					Product product = findProduct(pid);
					view.selectRow(product);
				} catch (NumberFormatException e) {
				}
			}
		}
	}


	private Product findProduct(int productId) {
		return DataService.get().getProductById(productId);
	}

	public void saveProduct(Product product) {
		view.showSaveNotification(product.getProductName() + " ("
				+ product.getId() + ") updated");
		view.selectRow(null);
		refreshTable();
		setFragmentParameter("");
	}

	public void deleteProduct(Product product) {
		DataService.get().deleteProduct(product.getId());
		view.showSaveNotification(product.getProductName() + " ("
				+ product.getId() + ") removed");

		view.selectRow(null);
		refreshTable();
		setFragmentParameter("");
	}


	public void editProduct(Product product) {
		if (product == null)
			setFragmentParameter("");
		else {
			setFragmentParameter(product.getId() + "");
		}
		view.editProduct(product);
	}

	private void refreshTable() {
		Product oldSelection = view.getSelectedRow();
		view.showProducts(DataService.get().getAllProducts());
		view.selectRow(oldSelection);
	}

	public void newProduct() {
		view.selectRow(null);
		setFragmentParameter("new");
		editProduct(new Product());
	}

	public void rowSelected(Product product) {
		view.editProduct(product);
		
	}
}
