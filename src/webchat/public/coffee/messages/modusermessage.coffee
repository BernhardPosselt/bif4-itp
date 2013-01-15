angular.module('WebChat').factory '_ModUserMessage', ['_Message', (_Message)->

    class ModUserMessage extends _Message

        constructor: (@userId, @channelId, @value) ->
            super('moduser')


        serialize: ->
            data = 
                id: @channelId
                users: @userId
                value: @value
            return super(data)


    return ModUserMessage

]