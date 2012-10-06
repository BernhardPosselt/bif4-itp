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

                $scope.getUserFullName = (userId) =>
                    user = @usermodel.getItemById(userId)
                    return user.getFullName()


        return MessageController

    ]