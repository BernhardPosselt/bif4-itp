angular.module('WebChat').factory '_FilesInChannelController', 
    ['_Controller', '_DeleteFileMessage', 'FileModel', 'MimeTypes', 'ChannelModel',
     (_Controller, _DeleteFileMessage, FileModel, MimeTypes, ChannelModel) ->

        class FilesInChannelController extends _Controller

            constructor: ($scope) ->
                super($scope)

                @filemodel = FileModel
                @channelmodel = ChannelModel

                $scope.files = @filemodel.getItems()

                $scope.getActiveChannel = () =>
                    if @getActiveChannelId() != null
                        return @channelmodel.getItemById(@getActiveChannelId())
                    else
                        return null

                $scope.getMimeTypeImage = (fileId) =>
                    file = @filemodel.getItemById(fileId)
                    if file != undefined
                        mime = MimeTypes.getIconPath(file.mimetype)
                        css = 'background-image: url("' + mime + '")'
                        return css
                        
                $scope.deleteFile = (fileId) =>
                    message = new _DeleteFileMessage(fileId)
                    @sendMessage(message)


        return FilesInChannelController

    ]
    