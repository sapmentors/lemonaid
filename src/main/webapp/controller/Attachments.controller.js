sap.ui.define([
    "com/sap/mentors/lemonaid/controller/BaseController",
    "sap/ui/model/json/JSONModel",
    "sap/m/MessageBox"
], function(BaseController, JSONModel, MessageBox) {
    "use strict";

    return BaseController.extend("com.sap.mentors.lemonaid.controller.Attachments", {

    	busyDialog: new sap.m.BusyDialog(),
    	
    	getComponent: function(control) {
    		return ( control instanceof sap.ui.core.mvc.View && 
    			     control.getController() && 
    			     control.getController().getOwnerComponent() ) ?
    						control.getController().getOwnerComponent() :
    						( control.getParent() ? this.getComponent(control.getParent()) : null );
    	},

		onBeforeUploadStarts: function(oEvent) {
			oEvent.getParameters().addHeaderParameter(
				new sap.m.UploadCollectionParameter({
					name: "slug",
					value: oEvent.getParameter("fileName")
				})
			);
			var i18n = this.getView().getModel("i18n").getResourceBundle();
			this.busyDialog.setTitle(i18n.getText("attachment"));
			this.busyDialog.setText(i18n.getText("uploadingAttachment", [ oEvent.getParameter("fileName") ]));
			this.busyDialog.open();
		},
		
		onUploadComplete: function(event) {
			var result = event.getParameter("mParameters"); 
			if (result.status >= 400) {
				var i18n = this.getView().getModel("i18n").getResourceBundle();
				var component = this.getComponent(this.getView());
				MessageBox.error(
						i18n.getText("errorDuringUpload", [ result.status, result.responseRaw]), {
						styleClass: component ? component.getContentDensityClass() : ""
				     });
			}
			this.getView().getModel().read(this.getView().getBindingContext().getPath() + "/Attachments");
			this.getView().getModel().refresh();
			this.busyDialog.close();
		},
		
		onFileDeleted: function(event) {
			this.getView().getModel().remove(event.getParameter("item").getBindingContext().getPath());
		},
		
		onDownloadItems: function(oEvent){
			var oUploadCollection = this.getView().byId("UploadCollection");
			var aSelectedItems = oUploadCollection.getSelectedItems();
			if (aSelectedItems){
				for (var i = 0; i < aSelectedItems.length; i++){
					oUploadCollection.downloadItem(aSelectedItems[i], true);
				}
			} else {
				var i18n = this.getView().getModel("i18n").getResourceBundle();
				var component = this.getComponent(this.getView());
				MessageBox.information(
						i18n.getText("selectAttachmentsFirst"), {
						styleClass: component ? component.getContentDensityClass() : ""
				     });
			}
		}

    });

});