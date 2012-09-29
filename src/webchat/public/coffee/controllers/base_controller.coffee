window.WebChat or= {}

window.WebChat.BaseController = class 

    constructor: (@$scope, @type) ->
        $scope.create = (item) ->
            $scope.items.push(item)

        $scope.update = (updatedItem) ->
            for item, counter in $scope.items
                if item.id == updatedItem.id
                    $scope.items[counter] = updatedItem;

        $scope.delete = (removedItem) ->
            removeItemId = -1
            for item, counter in $scope.items
                if item.id == removedItem.id
                    removeItemId = counter
            if removeItemId >= 0
                $scope.items.splice(removeItemId, 1)


    canHandle: (messageType) ->
        return messageType == @type