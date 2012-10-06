angular.module('WebChat').factory '_MessageController', 
    ['_Controller', '_SendMessage', 'GroupModel', 'UserModel', 'MessageModel',
     (_Controller, _SendMessage, GroupModel, UserModel, MessageModel) ->

        class MessageController extends _Controller

            constructor: ($scope) ->
                super($scope)

                @groupmodel = GroupModel
                @usermodel = UserModel
                @messagemodel = MessageModel

        return MessageController

    ]