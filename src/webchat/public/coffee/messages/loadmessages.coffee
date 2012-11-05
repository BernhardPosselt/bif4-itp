angular.module('WebChat').factory '_LoadMessages', ['_Message', (_Message)->

    class LoadMessages extends _Message
    
        constructor: (@channelId, @messageId, @earliestTimestamp) ->
            super('loadmessages')


        serialize: ->
            data = 
                channel_id: @channelId
                message_id: @messageId
                earliest_timestamp: @earliestTimestamp
            return super(data)         


    return LoadMessages

]