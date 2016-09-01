//jshint node:true

"use strict";

module.exports = function(grunt) {

	var ui5version = ""; //jshint ignore:line
    var uirepo     = "sapui5.hana.ondemand.com"; //jshint ignore:line

	// measures the time each task takes
	require("time-grunt")(grunt);

	// Project configuration.
	grunt.initConfig({

		// Configuration to be run (and then tested).
	    openui5_preload: { //jshint ignore:line
	        component: {
	            options: {
	                resources: {
	                    cwd: "src/main/webapp", // path to app root folder
	                    src: [
						  "**/*.js",
						  "**/*.fragment.html",
						  "**/*.fragment.json",
						  "**/*.fragment.xml",
						  "**/*.view.html",
						  "**/*.view.json",
						  "**/*.view.xml",
						  "**/*.properties"
						],
	                    prefix: "com/sap/mentors/lemonaid" // namespace prefix (in case the namespace is not already in folder structure like sap/ui/core/**)
	                },
					compress: true,
					dest: "src/main/webapp"
	            },
	            components: true
	        }
	    },

		// Local server
		connect: {
            server: {
                options: {
                    port: 8000,
                    hostname: "localhost",
                    base: {
                        path: "src/main/webapp",
                        index: "index.html",
                    },
                    keepalive: false,
                    livereload: true,
                    //open: true,
                    middleware: function (connect, options, defaultMiddleware) {
                        return [require("grunt-connect-proxy/lib/utils").proxyRequest].concat(defaultMiddleware);
                    }
                },
                proxies: [
					{
                        context: "/odata.svc",
                        host: "lemonaida5a504e08.hana.ondemand.com",
                        changeOrigin: true,
                        port: 443,
                        https: true
                    },
					{
                        context: "/userapi",
                        host: "lemonaida5a504e08.hana.ondemand.com",
                        changeOrigin: true,
                        port: 443,
                        https: true
                    }
                ]
            }
        },

		// validate JS files
        jshint: {
            files: [
            	"Gruntfile.js",
				"src/main/webapp/**/*.js",
				"!src/main/webapp/util/**"
			],
            options: {
                jshintrc: ".jshintrc"
            }
        },

		//validate XML files (XML Views)
        xml_validator: {                //jshint ignore:line
            your_target: {              //jshint ignore:line
                src: ["src/main/webapp/**/*.xml"]
            },
        },

		//watch for changed files and reload browser
        watch: {
			livereload: {
				options: {
					livereload: 35729,
					keepAlive:true
				},
				tasks: ["test"],
				files: ["<%= jshint.files %>", "<%= xml_validator.your_target.src %>"]
			},
        }

	});

	grunt.loadNpmTasks("grunt-openui5");
	grunt.loadNpmTasks("grunt-connect-proxy");
    grunt.loadNpmTasks("grunt-contrib-connect");
    grunt.loadNpmTasks("grunt-xml-validator");
    grunt.loadNpmTasks("grunt-contrib-jshint");
    grunt.loadNpmTasks("grunt-contrib-watch");

	// grunt.registerTask("default", [ "openui5_preload"]);

	grunt.registerTask("validate", [
		"jshint",
		"xml_validator"]);

	grunt.registerTask("default", [
		"jshint",
		"xml_validator",
		"configureProxies:server",
        "connect:server",
        "watch"]);

};
