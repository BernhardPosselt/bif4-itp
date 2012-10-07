angular.module('WebChat').factory '_Model', () ->

    Model = class

        constructor: (@modelType) ->
            @items = []
            @hashMap = {} # hashmap for quick access via item id


        handle: (message) ->
            switch message.action
                when 'create' then @create(message.data)
                when 'update' then @update(message.data)
                when 'delete' then @delete(message.data)


        create: (item) ->
            @hashMap[item.id] = item
            @items.push(item)


        update: (updatedItem) ->
            for item, counter in @items
                if item.id == updatedItem.id
                    @items[counter] = updatedItem;


        delete: (removedItem) ->
            removeItemId = -1
            for item, counter in @items
                if item.id == removedItem.id
                    removeItemId = counter
            if removeItemId >= 0
                @items.splice(removeItemId, 1)
                delete @hashMap[removedItemId]


        getItemById: (id) ->
            return @hashMap[id]

        getItems: ->
            return @items


        canHandle: (msgType) ->
            if msgType == @modelType
                return true
            else
                return false

    return Model