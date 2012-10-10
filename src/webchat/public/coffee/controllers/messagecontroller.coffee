angular.module('WebChat').factory '_MessageController', 
    ['_Controller', '_SendMessage', 'GroupModel', 'UserModel', 'MessageModel',
     'ChannelModel', '$filter'
     (_Controller, _SendMessage, GroupModel, UserModel, MessageModel, 
      ChannelModel, $filter) ->

        class MessageController extends _Controller

            constructor: ($scope) ->
                super($scope)

                $("#input_field").keydown (e) =>
                    if e.keyCode == 9 # tab
                        @autoComplete($scope)
                        return false
                    if e.keyCode == 13 and not e.shiftKey # enter
                        $("#input_send").trigger('click')
                        return false

                @resetInput($scope)

                @groupmodel = GroupModel
                @usermodel = UserModel
                @messagemodel = MessageModel
                @channelmodel = ChannelModel

                $scope.getChannels = =>
                    return @channelmodel.getItems()
                $scope.getUsers = =>
                    return @usermodel.getItems()
                $scope.getGroups = =>
                    return @groupmodel.getItems()
                $scope.getMessages = =>
                    return @messagemodel.getItems()

                $scope.getUserFullName = (userId) =>
                    user = @usermodel.getItemById(userId)
                    return user.getFullName()

                $scope.sendInput = (text, messageType, channelId) =>
                    if text != ''
                        message = new _SendMessage(text, messageType, channelId)
                        @sendMessage(message)
                        @resetInput($scope)


            resetInput: ($scope) ->
                $scope.messageType = 'text'
                $scope.textInput = ''


            autoComplete: ($scope) ->
                # deconstruct the sentence plus the part we want to autocomplete
                toComplete = ''
                toAppend = ''
                if $scope.textInput.length == 0
                    return
                else
                    words = $scope.textInput.split(' ')
                    toComplete = words[words.length - 1]
                    if words.length >= 2
                        words.pop()  # remove the last word that we want to complete
                        toAppend = words.join(' ')
                        toAppend +=  ' '

                # loop through all users in the channel to see if we can 
                # autocomplete
                userIds = {}
                channel = @channelmodel.getItemById(@getActiveChannelId())
                users = @usermodel.getItems()

                for userId in channel.users
                    userIds[userId] = true

                userInGroupFilter = $filter('userInGroup')
                for groupId in channel.groups
                    for user in userInGroupFilter(users, groupId)
                        userIds[user.id] = true

                for userId, bool of userIds
                    user = @usermodel.getItemById(userId)
                    fullName = user.firstname + user.lastname
                    if fullName.toLowerCase().indexOf(toComplete.toLowerCase()) == 0
                        $scope.$apply -> 
                            $scope.textInput = toAppend + fullName


        return MessageController

    ]