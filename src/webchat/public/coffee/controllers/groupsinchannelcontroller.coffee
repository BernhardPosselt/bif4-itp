angular.module('WebChat').factory '_GroupsInChannelController', 
    ['_Controller', '_ModUserMessage', '_ModGroupMessage', '_ReadonlyUserMessage', 
     '_ReadonlyGroupMessage', '_InviteGroupMessage', '_InviteUserMessage', 
     'ChannelModel', 'GroupModel', 'UserModel',
     (_Controller, _ModUserMessage, _ModGroupMessage, _ReadonlyUserMessage, 
      _ReadonlyGroupMessage, _InviteGroupMessage, _InviteUserMessage, ChannelModel, 
      GroupModel, UserModel) ->

        class GroupsInChannelController extends _Controller

            constructor: ($scope) ->
                super($scope)

                @channelmodel = ChannelModel
                @groupmodel = GroupModel
                @usermodel = UserModel

                $scope.getGroups = =>
                    return @groupmodel.getItems()
                $scope.getUsers = =>
                    return @usermodel.getItems()

                $scope.getActiveChannel = () =>
                    if @getActiveChannelId() != null
                        return @channelmodel.getItemById(@getActiveChannelId())
                    else
                        return null

                $scope.inviteUser = (userId, value) =>
                    @simpleChannelMessage(userId, _InviteUserMessage, value)

                $scope.inviteGroup = (groupId, value) =>
                    @simpleChannelMessage(groupId, _InviteGroupMessage, value)

                $scope.modUser = (userId, value) =>
                    @simpleChannelMessage(userId, _ModUserMessage, value)

                $scope.modGroup = (groupId, value) =>
                    @simpleChannelMessage(groupId, _ModGroupMessage, value)

                $scope.readonlyUser = (userId, value) =>
                    @simpleChannelMessage(userId, _ReadonlyUserMessage, value)

                $scope.readonlyGroup = (groupId, value) =>
                    @simpleChannelMessage(groupId, _ReadonlyGroupMessage, value)


        return GroupsInChannelController

    ]