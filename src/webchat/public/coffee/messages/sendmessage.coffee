angular.module('WebChat').factory '_SendMessage', ['_Message', (_Message)->

    class SendMessage extends _Message    

        constructor: (@text, @messageType, @channelId) ->
            super('message')


        serialize: ->
            data = 
                'message': @text
                'type': @messageType
                'channel_id': @channelId
            return super(data)            


    return SendMessage

]