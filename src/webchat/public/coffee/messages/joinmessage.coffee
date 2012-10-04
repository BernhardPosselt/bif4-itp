angular.module('WebChat').factory '_JoinMessage', ['_Message', (_Message)->

    class JoinMessage extends _Message

        constructor: (@id) ->
            super('join')


        serialize: ->
            data = 
                id: @id
            return super(data)


    return JoinMessage

]