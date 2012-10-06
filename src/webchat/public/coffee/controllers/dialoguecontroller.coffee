angular.module('WebChat').factory '_DialogueController', ['_Controller', (_Controller) ->
    
    class DialogueController extends _Controller

        constructor: ($scope, @channelmodel) ->
            super($scope)

            $scope.showNewChannelDialogue = (show) =>
                $scope.newChannelDialogue = show

    return DialogueController

]