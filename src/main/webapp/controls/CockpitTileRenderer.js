jQuery.sap.declare('cockpit.shared.ui.CockpitTileRenderer');
cockpit.shared.ui.CockpitTileRenderer = {
    render: function(r, c) {
        r.write("<div tabindex=\"0\"");
        r.writeControlData(c);
        if (c.getBack()) {
            r.addClass('flipcard');
        } else {
            r.addClass('cockpitTileBorder');
            r.addClass('cockpitTileBackground');
            r.addStyle('padding', c.getPadding());
        }
        r.addStyle('height', c.getHeight());
        r.addStyle('width', c.getWidth());
        r.writeClasses();
        r.writeStyles();
        if (c.getGroupLabel()) {
            var a = sap.ui.getCore().getConfiguration().getAccessibility();
            if (a) {
                r.writeAccessibilityState(c, {
                    role: 'group',
                    label: c.getGroupLabel()
                });
            }
        }
        r.write('>');
        if (c.getBack()) {
            r.write("<div id=\""+c.getId()+" - flipDiv \"");
            if (c.getFlipped()) {
                r.addClass('flipped');
            }
            if (c.getBack()) {
                r.addClass('flipContent');
            }
            r.writeClasses();
            r.write('>');
        }
        if (c.getBack()) {
            this._renderTwoSided(r, c);
        } else {
            this._renderOneSided(r, c);
        }
        if (c.getBack()) {
            r.write('</div>');
        }
        r.write('</div>');
    },
    _renderOneSided: function(r, c) {
        r.renderControl(c.getContent());
    },
    _renderTwoSided: function(r, c) {
        r.write('<div');
        r.addClass('flipFrontSide');
        r.addClass('cockpitTileBorder');
        r.addClass('cockpitTileBackground');
        r.writeClasses();
        r.addStyle('padding', c.getPadding());
        r.writeStyles();
        r.write('>');
        r.renderControl(c.getContent());
        r.write('</div>');
        r.write('<div');
        r.addClass('flipBackSide');
        r.addClass('cockpitTileBorder');
        r.addClass('cockpitTileBackground');
        r.writeClasses();
        r.addStyle('padding', c.getPadding());
        r.writeStyles();
        r.write('>');
        r.renderControl(c.getBack());
        r.write('</div>');
    }
};