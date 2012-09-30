window.WebChat or= {}

window.WebChat.BaseController = class 


    constructor: (@$scope, @type) ->
        @$scope.items = []
        @$scope.$on 'message', (scope, message) =>
            if @canHandle(message.type)
                @$scope.$apply =>
                    switch message.action
                        when 'update' then @update(message.data)
                        when 'create' then @create(message.data)
                        when 'delete' then @delete(message.data)


    create: (item) ->
        @$scope.items.push(item)


    update: (updatedItem) ->
        console.log @$scope.items
        console.log updatedItem
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


    getItemById: (id) ->
        for item in @$scope.items
            if item.id == id
                return item


    canHandle: (messageTypes) ->
        messageTypes = messageTypes.split('|')
        for type in messageTypes
            if type == @type
                return true
        return false