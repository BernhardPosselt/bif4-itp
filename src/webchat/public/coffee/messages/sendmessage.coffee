angular.module('WebChat').factory '_SendMessage', ['_Message', (_Message)->

    class SendMessage extends _Message    

        constructor: (@text, @type, @channelId) ->
            super('message')


        serialize: ->
            data = 
                'message': @text
                'type': @type
                'channel_id': @channelId
            return super(data)            


    return SendMessage

]