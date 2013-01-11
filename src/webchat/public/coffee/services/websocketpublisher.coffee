angular.module('WebChat').factory '_WebSocketPublisher', ->
        
    class WebSocketPublisher

        constructor: () ->
            @subscriptions = {}

        subscribe: (type, object) ->
            @subscriptions[type] or= []
            @subscriptions[type].push(object)


        publish: (type, message) ->
            for subscriber in @subscriptions[type] || []
                subscriber.handle(message)

    return WebSocketPublisher
