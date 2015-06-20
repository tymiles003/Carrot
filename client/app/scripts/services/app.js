'use strict';

/**
 * @ngdoc service
 * @name Carrot.App
 * @description
 * # Carrot
 * Factory in Carrot.
 */
angular.module('Carrot')
    .factory('App', function ($resource) {
        return $resource(baseURL + '/client/apps/:id');
    });