angular.module('WebChat').factory '_FilesInChannelController', 
    ['_Controller', '_DeleteFileMessage', 'FileModel',
     (_Controller, _DeleteFileMessage, FileModel) ->

        class FilesInChannelController extends _Controller

            constructor: ($scope) ->
                super($scope)

                @filemodel = FileModel

        return FilesInChannelController

    ]