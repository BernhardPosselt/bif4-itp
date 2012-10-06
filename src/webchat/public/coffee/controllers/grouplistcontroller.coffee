angular.module('WebChat').factory '_GroupListController', 
    ['_Controller', '_InviteUserMessage', '_InviteGroupMessage', 'GroupModel', 
     'UserModel',
     (_Controller, _InviteUserMessage, _InviteGroupMessage, GroupModel, UserModel) ->

        class GroupListController extends _Controller

            constructor: ($scope) ->
                super($scope)

                @groupmodel = GroupModel
                @usermodel = UserModel

                $scope.inviteUser = (userId, value) =>
                    @simpleChannelMessage(userId, _InviteUserMessage, value)

                $scope.inviteGroup = (groupId, value) =>
                    @simpleChannelMessage(groupId, _InviteGroupMessage, value)

        return GroupListController

    ]