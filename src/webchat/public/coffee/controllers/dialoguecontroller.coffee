angular.module('WebChat').factory '_DialogueController', ->
    class DialogueController

        constructor: ($scope, @activechannel, @channelmodel) ->

            $scope.showNewChannelDialogue = (show) =>
                $scope.newChannelDialogue = show

    return DialogueController