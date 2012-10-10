angular.module('WebChat').factory '_DialogueController', ['_Controller', (_Controller) ->
    
    class DialogueController extends _Controller

        constructor: ($scope) ->
            super($scope)

            $scope.showNewChannelDialogue = (show) =>
                $scope.newChannelDialogue = show

            $scope.setNewChannelDialogue = (show) =>
                $scope.newChannelDialogue = show

    return DialogueController

]