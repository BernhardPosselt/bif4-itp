###
Manages all channel objects in the channellist
###

class ChannelManager

    constructor: () ->
        @dom_elem = $ "#channels ul"
        @channel_list = null
        @cache = []

    init: (data) ->
        @channel_list = data
        @process_cache()

    update: (data) ->
        if @channel_list is null
            # append to cache if init was not performed
            #@cache[]
        else
            # 
            # if @cache
          #@
          
    # happens if an entry does exist but contains newer information 
    migrate: (entry) ->
      
    # happens if an entry does not yet exist
    insert: (entry) ->

  
