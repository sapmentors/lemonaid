/* global sap */

sap.ui.define([
	"sap/m/Select",
	"sap/m/SelectRenderer",
	"sap/m/SelectList",
	"sap/ui/core/Item"
], function (BaseControl, SelectRenderer, SelectList, Item) {
	"use strict";

	var Select = BaseControl.extend("com.sap.mentors.lemonaid.controls.Select", {

		metadata : {
			properties : {
				nullable: { type: "boolean", group : "Behavior", defaultValue : false }
			}
		},

		renderer: SelectRenderer.render,

	});

	/**
	 * Called whenever the binding of the aggregation items is changed.
	 *
	 */
	Select.prototype.updateItems = function(sReason) {
		SelectList.prototype.updateItems.apply(this, arguments);

		if (this.getNullable()) {
			this.getList().insertItem(new Item({ key: null, text: "" }), 0);
		}
		
		// note: after the items are recreated, the selected item association
		// points to the new item
		this._oSelectionOnFocus = this.getSelectedItem();
	};
	
	return Select;

});
