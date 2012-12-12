angular.module('WebChat').factory '_Model', ['$rootScope', ($rootScope) ->

    Model = class

        constructor: (@modelType) ->
            @items = []
            @hashMap = {} # hashmap for quick access via item id


        handle: (message) ->
            $rootScope.$apply =>
                switch message.action
                    when 'create' then @create(message.data)
                    when 'update' then @update(message.data)
                    when 'delete' then @delete(message.data)


        create: (item) ->
            if @hashMap[item.id] != undefined
                @update(item)
            else
                @hashMap[item.id] = item
                @items.push(item)


        update: (item) ->
            updatedItem = @hashMap[item.id]
            for key, value of item
                if key != 'id'
                    updatedItem[key] = value


        delete: (removedItem) ->
            removeItemId = -1
            for item, counter in @items
                if item.id == removedItem.id
                    removeItemId = counter
            if removeItemId >= 0
                @items.splice(removeItemId, 1)
                delete @hashMap[removedItem.id]


        getItemById: (id) ->
            return @hashMap[id]

        getItems: ->
            return @items


        getModelType: ->
            return @modelType

    return Model

]