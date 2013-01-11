angular.module('WebChat').factory '_ActiveUser', () ->
    
    class ActiveUser

        constructor: ->
            @id = null


        getModelType: ->
            return 'activeuser'

        handle: (message) ->
            @id = message.data.id


    return ActiveUser
