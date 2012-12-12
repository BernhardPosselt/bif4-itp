angular.module('WebChat').factory '_ChangeTopicMessage', ['_Message', (_Message)->

    class ChangeTopicMessage extends _Message

        constructor: (@id, @topic) ->
            super('channeltopic')


        serialize: ->
            data = 
                id: @id
                topic: @topic
            return super(data)


    return ChangeTopicMessage

]