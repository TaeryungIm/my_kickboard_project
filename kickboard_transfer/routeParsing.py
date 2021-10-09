import json
counter = 0

json_file = open('out.json', 'rt')
road_data = json.load(json_file)

coordinates = []
#print(road_data['route']['trafast'])
for subdata in road_data['route']['traavoidcaronly']:
    for routes in subdata['path']:
        coordinates.append(routes)
        counter += 1

write_file = open("path.txt", 'w')
write_file.write(str(counter))
write_file.write("\n")
for coor in coordinates:
    write_file.write(str(coor[0]))
    write_file.write(" ")
    write_file.write(str(coor[1]))
    write_file.write("\n")

write_file.close()
