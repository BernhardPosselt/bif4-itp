angular.module('WebChat').factory '_NewChannelMessage', ['_Message', (_Message)->

    class NewChannelMessage extends _Message

        constructor: (@name, @topic, @isPublic) ->
            super('newchannel')


        serialize: ->
            data = 
                name: @name
                topic: @topic
                is_public: @isPublic
            return super(data)


    return NewChannelMessage

]