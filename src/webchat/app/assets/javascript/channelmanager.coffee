###
Manages all channel objects in the channellist
###

class ChannelManager

  constructor: () ->
    @dom_elem = $ "#channels ul"
    @list = []



  update: (list) ->
    # TODO: iterate through list, check if channel is 
    # not in the channel list, if not push it into the channellist
    # update the elements properties
    # sort the list by the last update date
    alert list["0"]["name"]
	alert "hi"
  
  insert: (elem, position) ->
    @list[positions] = elem
  
