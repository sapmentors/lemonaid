'use strict';

module.exports = function(grunt) {

	// Project configuration.
	grunt.initConfig({

		// Configuration to be run (and then tested).
	    openui5_preload: {
	        component: {
	            options: {
	                resources: {
	                    cwd: 'src/main/webapp', // path to app root folder
	                    src: [
						  '**/*.js',
						  '**/*.fragment.html',
						  '**/*.fragment.json',
						  '**/*.fragment.xml',
						  '**/*.view.html',
						  '**/*.view.json',
						  '**/*.view.xml',
						  '**/*.properties'
						],
	                    prefix: 'com/sap/mentors/lemonaid' // namespace prefix (in case the namespace is not already in folder structure like sap/ui/core/**)
	                },
					dest: 'dist',
					compress: true
	            },
	            components: true
	        }
	    }

	});

	// Actually load this plugin's task(s).
	grunt.loadNpmTasks('grunt-openui5');

	// By default, lint and run all tests.
	grunt.registerTask('default', [ 'openui5_preload']);

};
