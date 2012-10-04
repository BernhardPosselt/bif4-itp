webchat = require('webchat')

webchat.Model = class

    constructor: (@type) ->
        @items = []


    handle: (message) ->
        switch message.action
            when 'update' then @update(message.data)
            when 'create' then @create(message.data)
            when 'delete' then @delete(message.data)


    create: (item) ->
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


    getItemById: (id) ->
        for item in @items
            if item.id == id
                return item


    canHandle: (messageTypes) ->
        if type == @type
            return true
        else
            return false
