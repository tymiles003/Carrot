'use strict';

/**
 * @ngdoc function
 * @name Carrot.filters:capitalize
 * @description
 * # capitalize
 *
 * Filter fpr capitalizing a string.
 */
angular.module('Carrot')
    .filter('capitalize', function () {
        return function (input, scope) {
            if (input == null) {
                return;
            }

            input = input.toLowerCase();
            return input.substring(0, 1).toUpperCase() + input.substring(1);
        }
    });