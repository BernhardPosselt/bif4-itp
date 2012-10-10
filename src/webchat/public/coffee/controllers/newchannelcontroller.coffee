angular.module('WebChat').factory '_NewChannelController', 
    ['_Controller', '_NewChannelMessage', (_Controller, _NewChannelMessage) ->
        
        class NewChannelController extends _Controller

            constructor: ($scope) ->
                super($scope)                
                @resetInput($scope)

                $scope.createNewChannel = (name, topic, isPublic) =>
                    @resetInput($scope)
                    message = new _NewChannelMessage(name, topic, isPublic)
                    @sendMessage(message)
                    $scope.setNewChannelDialogue(false)


            resetInput: ($scope) ->
                $scope.newChannelName = ''
                $scope.newChannelTopic = ''
                $scope.newChannelPublic = false

        return NewChannelController

    ]