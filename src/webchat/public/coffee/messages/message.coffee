angular.module('WebChat').factory '_Message', () ->

    Message = class

        constructor: (@type) ->


        serialize: (data) ->
            message =
                type: @type
                data: data
            return message

    return Message
