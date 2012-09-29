window.WebChat or= {}

window.WebChat.BaseController = class 

    constructor: (@$scope, @type) ->


    create: (item) ->
        @$scope.items.push(item)


    update: (updatedItem) ->
        for item, counter in @$scope.items
            if item.id == updatedItem.id
                @$scope.items[counter] = updatedItem;


    delete: (removedItem) ->
        removeItemId = -1
        for item, counter in @$scope.items
            if item.id == removedItem.id
                removeItemId = counter
        if removeItemId >= 0
            @$scope.items.splice(removeItemId, 1)


    canHandle: (messageTypes) ->
        messageTypes = messageTypes.split('|')
        for type in messageTypes
            if type == @type
                return true
        return false