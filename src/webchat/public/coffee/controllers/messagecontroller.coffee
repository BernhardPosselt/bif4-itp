angular.module('WebChat').factory '_MessageController', 
    ['_Controller', '_SendMessage', 'GroupModel', 'UserModel', 'MessageModel',
     'ChannelModel',
     (_Controller, _SendMessage, GroupModel, UserModel, MessageModel, ChannelModel) ->

        class MessageController extends _Controller

            constructor: ($scope) ->
                super($scope)

                @groupmodel = GroupModel
                @usermodel = UserModel
                @messagemodel = MessageModel
                @channelmodel = ChannelModel

                $scope.channels = @channelmodel.getItems()
                $scope.users = @usermodel.getItems()
                $scope.groups = @groupmodel.getItems()
                $scope.messages = @messagemodel.getItems()

                $scope.messageType = 'text'

                $scope.getUserFullName = (userId) =>
                    user = @usermodel.getItemById(userId)
                    return user.getFullName()

                $scope.sendInput = (text, messageType, channelId) =>
                    console.log messageType
                    message = new _SendMessage(text, messageType, channelId)
                    @sendMessage(message)

        return MessageController

    ]