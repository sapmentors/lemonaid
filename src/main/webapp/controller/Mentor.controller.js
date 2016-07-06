sap.ui.define([
    "com/sap/mentors/lemonaid/controller/BaseController",
    "sap/m/MessageBox"
], function(BaseController, MessageBox) {
    "use strict";

    return BaseController.extend("com.sap.mentors.lemonaid.controller.Mentor", {
    	
    	busyDialog: new sap.m.BusyDialog(),

        onInit: function() {
        	this.view = this.getView();
        	this.component = this.getComponent();
        	this.model = this.component.getModel();
        	this.router = this.getRouter();
        	this.i18n = this.component.getModel("i18n").getResourceBundle();
            this.router.getRoute("Mentor").attachMatched(this.onRouteMatched, this);
        },

        onRouteMatched: function(oEvent) {
            this.sMentorId = oEvent.getParameter("arguments").Id;
            this.getModel().metadataLoaded().then(this.bindView.bind(this));
        },

        bindView: function() {
            this.getView().bindElement({
                path: this.getModel().createKey("/Mentors", { Id: this.sMentorId }),
                parameters: {
                    expand: 'MentorStatus,RelationshipToSap,Country,Topic1,Topic2,Topic3'
                }
            });
			var uploadControl = this.view.byId("UploadCollection");
			uploadControl.setUploadUrl(
				this.model.sServiceUrl + "/" + this.model.createKey("Mentors", {Id: this.sMentorId}) + "/Attachments");
			uploadControl.bindElement({
				path: "/" + this.model.createKey("Mentors", {Id: this.sMentorId})
			});
        },

		onBeforeUploadStarts: function(oEvent) {
			oEvent.getParameters().addHeaderParameter(
				new sap.m.UploadCollectionParameter({
					name: "slug",
					value: oEvent.getParameter("fileName")
				})
			);
//			oEvent.getParameters().addHeaderParameter(
//				new sap.m.UploadCollectionParameter({
//					name: "x-csrf-token",
//					value: this.model.getHeaders()["x-csrf-token"]
//				})
//			);
			this.busyDialog.setTitle(this.i18n.getText("attachment"));
			this.busyDialog.setText(this.i18n.getText("uploadingAttachment", [ oEvent.getParameter("fileName") ]));
			this.busyDialog.open();
		},
		
		onUploadComplete: function(event) {
			var result = event.getParameter("mParameters"); 
			if (result.status >= 400) {
				MessageBox.error(
						this.i18n.getText("errorDuringUpload", [ result.status, result.responseRaw]), {
						styleClass: this.component.getContentDensityClass()
				     });
			}
			this.model.read("/" + this.model.createKey("Mentors", {Id: this.sMentorId}) + "/Attachments");
			this.model.refresh();
			this.busyDialog.close();
		},
		
		onChange: function(oEvent) {
			var oUploadCollection = oEvent.getSource();
			var oRequest = this.model._createRequest();  
			var oCustomerHeaderToken = new UploadCollectionParameter({
				name: "x-csrf-token",
				value: oRequest.headers['x-csrf-token']
			});
			oUploadCollection.addHeaderParameter(oCustomerHeaderToken);
		},
		
		onFileDeleted: function(event) {
			this.model.remove(event.getParameter("item").getBindingContext().getPath());
		},
		
		onDownloadItems: function(oEvent){
			var oUploadCollection = this.getView().byId("UploadCollection");
			var aSelectedItems = oUploadCollection.getSelectedItems();
			if (aSelectedItems){
				for (var i = 0; i < aSelectedItems.length; i++){
					oUploadCollection.downloadItem(aSelectedItems[i], true);
				}
			} else {
				MessageBox.information(
						this.i18n.getText("selectAttachmentsFirst"), {
						styleClass: this.component.getContentDensityClass()
				     });
			}
		}
        
    });
});

