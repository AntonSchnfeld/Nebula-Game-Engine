#version 330 core

in vec2 outTexCoord;
in vec4 outCol;

uniform sampler2D texture_sampler;
uniform float texture_id;

out vec4 pixelCol;

void main()
{
    // Draw image if it is passed in, else draw color
    if (texture_id > -1)
    {
        pixelCol = outCol * texture(texture_sampler, outTexCoord);
    }
    else
    {
        pixelCol = outCol;
    }
}
