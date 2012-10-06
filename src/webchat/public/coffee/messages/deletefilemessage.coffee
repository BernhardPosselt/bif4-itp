angular.module('WebChat').factory '_DeleteFileMessage', ['_Message', (_Message)->

    class DeleteFileMessage extends _Message

        constructor: (@fileId) ->
            super('filedelete')


        serialize: ->
            data = 
                id: @fileId
            return super(data)


    return DeleteFileMessage
]