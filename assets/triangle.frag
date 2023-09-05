#version 330 core

in vec4 fCol;
in vec2 fUV;

uniform sampler2D texture_1;
uniform sampler2D texture_2;
uniform sampler2D texture_3;
uniform sampler2D texture_4;
uniform sampler2D texture_5;
uniform sampler2D texture_6;
uniform sampler2D texture_7;
uniform sampler2D texture_8;
uniform sampler2D texture_9;
uniform sampler2D texture_10;
uniform float texture_id;

out vec4 fragCol;

void main()
{
    if (texture_id < 0)
    {
        fragCol = fCol;
    }
    else if (texture_id == 1)
    {
        fragCol = fCol * texture(texture_1, fUV);
    }
    else if (texture_id == 2)
    {
        fragCol = fCol * texture(texture_2, fUV);
    }
    else if (texture_id == 3)
    {
        fragCol = fCol * texture(texture_3, fUV);
    }
    else if (texture_id == 4)
    {
        fragCol = fCol * texture(texture_4, fUV);
    }
    else if (texture_id == 5)
    {
        fragCol = fCol * texture(texture_5, fUV);
    }
    else if (texture_id == 5)
    {
        fragCol = fCol * texture(texture_5, fUV);
    }
    else if (texture_id == 6)
    {
        fragCol = fCol * texture(texture_6, fUV);
    }
    else if (texture_id == 7)
    {
        fragCol = fCol * texture(texture_7, fUV);
    }
    else if (texture_id == 8)
    {
        fragCol = fCol * texture(texture_8, fUV);
    }
    else if (texture_id == 9)
    {
        fragCol = fCol * texture(texture_9, fUV);
    }
    else if (texture_id == 10)
    {
        fragCol = fCol * texture(texture_10, fUV);
    }

}