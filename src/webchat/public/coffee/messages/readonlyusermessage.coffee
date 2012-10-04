angular.module('WebChat').factory '_ReadonlyUserMessage', ['_Message', (_Message)->

    class ReadonlyUserMessage extends _Message
    
        constructor: (@userId, @channelId, @value) ->
            super('readonlyuser')


        serialize: ->
            data = 
                channel_id: @channelId
                user_id: @userId
                value: @value
            return super(data)     


    return ReadonlyUserMessage

]