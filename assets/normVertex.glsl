#version 330 core

layout (location = 0) in vec3 aPos; // Vertex Position
layout (location = 1) in vec4 aCol; // Color of the vertex
layout (location = 2) in vec2 texCoord; // Texture UV coordinate

uniform mat4 aProjection;
uniform mat4 aView;

out vec2 outTexCoord;
out vec4 outCol;

void main()
{
    gl_Position = aProjection * aView * vec4(aPos, 1.0f);
    outTexCoord = texCoord;
    outCol = aCol;
}
