'use strict';

/**
 * @ngdoc function
 * @name Carrot.controller:AppController
 * @description
 * # AppController
 *
 * Controller showing apps
 */
angular.module('Carrot')
    .controller('AppController', function ($scope, $log, ngTableParams, App, Entity) {
        var data = App.query(function (data) {
            $scope.tableParams = new ngTableParams({
                page: 1,
                count: 10
            }, {
                total: data.length,
                getData: function ($defer, params) {
                    var pageData = data.slice((params.page() - 1) * params.count(), params.page() * params.count());
                    params.total(data.length);
                    $log.debug("data length " + pageData.length);

                    // Fix empty page after deletion
                    if (pageData.length == 0 && data.length > 0) {
                        pageData = data.slice((params.page() - 2) * params.count(), params.page() * params.count());
                    }

                    $defer.resolve(pageData);
                }
            })
        });

        $scope.delete = function (item) {
            Entity.delete(item, "App", function (object) {
                var index = data.indexOf(item);
                if (index > -1) {
                    data.splice(index, 1);
                }
                $scope.tableParams.reload()
            });
        };

        $scope.edit = function (item) {
            $log.debug("Edit item " + item);
        };
    });
