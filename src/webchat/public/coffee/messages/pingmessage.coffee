angular.module('WebChat').factory '_PingMessage', ['_Message', (_Message)->

    class PingMessage extends _Message

        constructor: () ->
            super('ping')


        serialize: ->
            data = {}
            return super(data)            


    return PingMessage

]