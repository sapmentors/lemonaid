'use strict';

module.exports = function(grunt) {

	var ui5version = ''
    var uirepo = 'sapui5.hana.ondemand.com'

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
					compress: true,
					dest: 'src/main/webapp'
	            },
	            components: true
	        }
	    },

		// Local server
		connect: {
            server: {
                options: {
                    port: 8000,
                    hostname: 'localhost',
                    base: {
                        path: 'src/main/webapp',
                        index: 'index.html',
                    },
                    keepalive: true,
                    open: true,
                    middleware: function (connect, options, defaultMiddleware) {
                        return [require('grunt-connect-proxy/lib/utils').proxyRequest].concat(defaultMiddleware);
                    }
                },
                proxies: [
					{
                        context: '/odata.svc',
                        host: 'lemonaida5a504e08.hana.ondemand.com',
                        changeOrigin: true,
                        port: 443,
                        https: true
                    },
					{
                        context: '/userapi',
                        host: 'lemonaida5a504e08.hana.ondemand.com',
                        changeOrigin: true,
                        port: 443,
                        https: true
                    }
                ]
            }
        }


	});

	grunt.loadNpmTasks('grunt-openui5');
	grunt.loadNpmTasks('grunt-connect-proxy');
    grunt.loadNpmTasks('grunt-contrib-connect');

	// grunt.registerTask('default', [ 'openui5_preload']);
	grunt.registerTask('default', [
        'configureProxies:server',
        'connect:server']);

};
