angular.module('WebChat').factory 'ActiveUser', () ->
    ActiveUser = 
        id: null

    ActiveUser.canHandle = (message) ->
        if message.type == 'activeuser'
        	return true
        else
        	return false

    ActiveUser.handle = (message) ->
        ActiveUser.id = message.data.id


    return ActiveUser
