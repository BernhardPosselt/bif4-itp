###
Caches datastructures clientside and builts complete JSON arrays
###

window.WebChat or= {}

window.WebChat.Cache = class 

    constructor: () ->
        @users = {}
        @channels = {}
        @groups = {}

    fill: (message) ->
        return message