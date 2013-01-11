angular.module('WebChat').factory '_ReadonlyGroupMessage', ['_Message', (_Message)->

    class ReadonlyGroupMessage extends _Message
    
        constructor: (@groupId, @channelId, @value) ->
            super('readonlygroup')


        serialize: ->
            data = 
                channel_id: @channelId
                user_id: @groupId
                value: @value
            return super(data)         


    return ReadonlyGroupMessage

]