angular.module('WebChat').factory '_DialogueController', ['_Controller', (_Controller) ->
    
    class DialogueController extends _Controller

        constructor: ($scope, @activeChannel, @channelModel, _NewChannelMessage
                    _ChangeTopicMessage, _CloseChannelMessage, 
                    _ChangeChannelNameMessage, _EditProfileMessage, @activeUser) ->
            super($scope)
            @resetNewChannelInput($scope)

            $scope.showNewChannelDialogue = (show) =>
                $scope.newChannelDialogue = show

            $scope.showCloseChannelDialogue = (show) =>
                $scope.closeChannelDialogue = show

            $scope.showChangeChannelNameDialogue = (show) =>
                $scope.changeChannelNameDialogue = show

            $scope.showChangeTopicDialogue = (show) =>
                $scope.changeTopicDialogue = show

            $scope.showEditProfileDialogue = (show) =>
                $scope.editProfileDialogue = show


            $scope.getActiveChannelName = =>
                id = @activeChannel.getActiveChannelId()
                channel = @channelModel.getItemById(id)
                if channel == undefined
                    return ''
                else
                    return channel.name

            $scope.getActiveChannelTopic = =>
                id = @activeChannel.getActiveChannelId()
                channel = @channelModel.getItemById(id)
                if channel == undefined
                    return ''
                else
                    return channel.topic

            $scope.createNewChannel = (name, topic, isPublic) =>
                @resetNewChannelInput($scope)
                message = new _NewChannelMessage(name, topic, isPublic)
                @sendMessage(message)
                $scope.showNewChannelDialogue(false)


            $scope.closeChannel = =>
                id = @activeChannel.getActiveChannelId()
                message = new _CloseChannelMessage(id)
                @sendMessage(message)
                $scope.showCloseChannelDialogue(false)


            $scope.changeChannelName = (channelName) =>
                id = @activeChannel.getActiveChannelId()
                message = new _ChangeChannelNameMessage(id, channelName)
                @sendMessage(message)
                $scope.showChangeChannelNameDialogue(false)                


            $scope.changeChannelTopic = (channelTopic) =>
                id = @activeChannel.getActiveChannelId()
                message = new _ChangeTopicMessage(id, channelTopic)
                @sendMessage(message)
                $scope.showChangeTopicDialogue(false) 
                $scope.channelTopic = ''

            $scope.changeProfile = (username, prename, lastname, password, email) =>
                id = @activeUser.id
                message = new _EditProfileMessage(id, username, prename, lastname,
                    password, email)
                @sendMessage(message)
                $scope.showEditProfileDialogue(false)


        resetNewChannelInput: ($scope) ->
            $scope.newChannelName = ''
            $scope.newChannelTopic = ''
            $scope.newChannelPublic = false

    return DialogueController

]